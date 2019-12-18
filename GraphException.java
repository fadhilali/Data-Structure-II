public class GraphException extends RuntimeException
{
   public GraphException(String str)
   {
      super(str);
   }
   public GraphException()
   {
      super("IDMsystem Graph Exception");
   }
}