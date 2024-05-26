import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RedisNioServer {
  private ServerSocketChannel serverSocketChannel;
  private Selector selector;
  private List<Byte> byteMessage = new ArrayList<>();
  private MyRedis redis;

  public RedisNioServer() throws IOException {
    this.redis=new MyRedis();
    this.serverSocketChannel = ServerSocketChannel.open();
    this.selector = Selector.open();
    serverSocketChannel.configureBlocking(false);
    SocketAddress socketAddress = new InetSocketAddress(6379);
    serverSocketChannel.socket().bind(socketAddress,1024);

    // ServerSocketChannel 과 Accept 이벤트 등록
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
  }

  public void run(){
    try {
      while(this.serverSocketChannel.isOpen()){
        selector.select();
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while(iterator.hasNext()){
          SelectionKey key = iterator.next();
          iterator.remove();
          if(key.isAcceptable())
            accept(key);
          else if(key.isWritable())
            response(key);
          else if(key.isReadable())
            read(key);
        }
      }
    }catch (Exception e){
      System.out.println("exception : "+e.getMessage());
      e.printStackTrace();
    }
  }
  private void accept(SelectionKey key)throws IOException{
    ServerSocketChannel server = (ServerSocketChannel) key.channel();
    SocketChannel socketChannel = server.accept();
    socketChannel.configureBlocking(false);
    socketChannel.register(this.selector,SelectionKey.OP_READ);
  }
  private void read(SelectionKey key)throws IOException{
    // SelectionKey로부터 소켓채널을 얻어온다.
    SocketChannel sc = (SocketChannel) key.channel();
    // ByteBuffer를 생성한다.
    ByteBuffer buffer = ByteBuffer.allocateDirect(64);
    int read = sc.read(buffer);
    if(read == -1) {
      sc.close();
      key.cancel();
      System.out.println("client closed");
      return;
    }
    buffer.flip();
    while(buffer.hasRemaining()){
      byteMessage.add(buffer.get());
    }
    String message = byteListToString(byteMessage,StandardCharsets.UTF_8);
    byte[] responseMessage = redis.request(message);
    buffer.compact();
    sc.register(this.selector, SelectionKey.OP_WRITE, appendRedisSuffix(responseMessage));
  }
  private void response(SelectionKey key)throws IOException{
    SocketChannel sc = (SocketChannel) key.channel();
    // ByteBuffer를 생성한다.
    ByteBuffer writeBuffer = ByteBuffer.allocateDirect(64);
    byte[] response = (byte[]) key.attachment();
    writeBuffer.put(response);
    writeBuffer.flip();
    sc.write(writeBuffer);
    sc.register(this.selector,SelectionKey.OP_READ);
  }
  private String byteListToString(List<Byte> l, Charset charset) {
    if (l == null) {
      return "";
    }
    byte[] array = new byte[l.size()];
    int i = 0;
    for (Byte current : l) {
      array[i] = current;
      i++;
    }
    return new String(array, charset);
  }
  private byte[] appendRedisSuffix(byte[] message){
    byte[] result = new byte[message.length+3];
    result[0]="+".getBytes(StandardCharsets.UTF_8)[0];
    result[result.length-1]="\n".getBytes(StandardCharsets.UTF_8)[0];
    result[result.length-2]="\r".getBytes(StandardCharsets.UTF_8)[0];
    for(int i=0;i<message.length;i++){
      result[1+i]=message[i];
    }
    return result;
  }
}
