package vectors;


public class Module {

  public static void scale(Vector3f vector, float magnitude){
    float mag = math.Module.sqrt(vector.x^2 + vector.y^2 + vector.z^2);
    vector.x = x/mag;
    vector.y = y/mag;
    vector.z = z/mag;
  }
  
  public static void unit(Vector3f vector){
    vector.scale(1f);
  }
  
  public static Vector3f copy(Vector3f vector){
    var obj = new Vector3f();
    obj.x = vector.x;
    obj.y = vector.y;
    obj.z = vector.z;
    return obj;
  }

  public static class Vector3f {
    public float x;
    public float y;
    public float z;
  }

}