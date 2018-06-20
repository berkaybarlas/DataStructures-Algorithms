package code;

import java.awt.Color;
import java.util.*;

import given.Image.PixelCoordinate;
import given.Image;

public class ImageSegmenter {

  // Colors to use while coloring
  private static Color[] colors = { Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY, Color.MAGENTA, Color.RED,
      Color.CYAN, Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.YELLOW, Color.DARK_GRAY };

  /*
   *
   * YOU CAN ADD MORE FIELDS HERE
   *
   */

  /**
   * Segment image by finding connected components. Pixels in same component must
   * have the same color. Coloring should be done on a new image which should be
   * returned. Note that you need to use getValidNeighbors.
   *
   * You can use any graph traversal method you like.
   *
   * @param epsilon
   *          - threshold value to decide connectedness of two neighboring pixels.
   */
  public Image segmentImage(Image input, double epsilon) {
    Image output = new Image(input.getHeight(), input.getWidth());
    int colorIndx = 0;
    int numColors = colors.length;
    //UndirectedUnweightedGraph<PixelCoordinate> graph = new UndirectedUnweightedGraph<>();
    //PixelCoordinate[][] matrix = new PixelCoordinate[input.getHeight()][input.getWidth()];
    //HashSet<PixelCoordinate> visited = new HashSet<>();
    //HashMap<PixelCoordinate,Color> map = new HashMap<>();
    int[][] visited = new int[input.getHeight()][input.getWidth()];
    //Stack<PixelCoordinate> stack = new Stack<>();
    Deque<PixelCoordinate> stack = new ArrayDeque<>();
    for (int c = 0; c < input.getWidth(); ++c) {
      for (int r = 0; r < input.getHeight(); ++r) {
      //if(r==20) break;
        // Get all possible neighbors of pixel at row r and column c for fun
        PixelCoordinate pc = new PixelCoordinate(r, c);
        Color nextColorToUse = colors[++colorIndx % numColors];

        if(visited[pc.r][pc.c]!=1) {
          stack.addLast(pc);
          while (!stack.isEmpty()) {
            PixelCoordinate u = stack.removeFirst();
            if (visited[u.r][u.c]!=1) {
              visited[u.r][u.c] = 1;
              output.setColor(u, nextColorToUse);
              for (PixelCoordinate p : neighbor(u, epsilon, input)) {
                if (visited[p.r][p.c]!=1)
                  stack.push(p);
              }
            }
          }
          colorIndx++;
        }
      }
    }
    //nextColorToUse = colors[++colorIndx % numColors];

    return output;
  }
  public List<PixelCoordinate> neighbor(PixelCoordinate pc,double epsilon,Image input){
    List<PixelCoordinate> list = new LinkedList<>();
    int r = pc.r;
    int c = pc.c;
    PixelCoordinate[] PixelCoordinateA = new PixelCoordinate[4];
    PixelCoordinateA[0]= new PixelCoordinate(r, -1);
    PixelCoordinateA[1] = new PixelCoordinate(r-1, c);
    PixelCoordinateA[2] = new PixelCoordinate(r, c+1);
    PixelCoordinateA[3] = new PixelCoordinate(r+1, c);
    for(int i = 0; i<4;i++){
      PixelCoordinate pcc =  PixelCoordinateA[i];
        if(compareColors(input,epsilon,pc,pcc)==1){
          list.add(pcc);
        }
      }
    return list;
  }
  public int compareColors(Image input,double e,PixelCoordinate a,PixelCoordinate b){
    if(b.r<0 || b.r>=input.getHeight() || b.c<0 || b.c>=input.getWidth()) return -1;
    if(Math.abs(input.getPixelVal(a.r,a.c)-input.getPixelVal(b.r,b.c)) <= 2.0*e ){
    return 1;
    }
    return 0;
  }
  public void recursiveDFS(BaseGraph<PixelCoordinate> G,Vertex<PixelCoordinate> v, Color color,Image output){

      //Vertex<PixelCoordinate> v = G.findVertex(startVertex.getElement());
      v.visit();
      output.setColor(v.getElement(),color );
      for(PixelCoordinate w : v.adjacent() ){
        Vertex<PixelCoordinate> vertexW = G.findVertex(w);
        if(vertexW!=null && !vertexW.isVisited()){
          recursiveDFS(G,vertexW,color,output);
        }
      }

  }

  /**
   * This function iterates over the image and colors output image using "color"
   * array in circular fashion. i.e. if all colors have been used then pick the
   * first color.
   * 
   * This is given to you as an example of how to use some of the available
   * classes,
   * 
   //* @param graph
   *          - image graph.
   //* @param output
   *          - output segmented image.
   */
  public Image dummyIteration(Image input) {
    int colorIndx = 0;
    int numColors = colors.length;
    Image output = new Image(input.getHeight(), input.getWidth());
    for (int r = 0; r < input.getHeight(); ++r) {
      for (int c = 0; c < input.getWidth(); ++c) {
        // Get all possible neighbors of pixel at row r and column c for fun
        PixelCoordinate pc = new PixelCoordinate(r, c);
        
        //HINT: You need to iterate over all the some of neighbors of the current pixel here!
        
        // Get the next color in circular fashion
        Color nextColorToUse = colors[colorIndx % numColors];
        // Set the color of pixel at PixelCoordinate pc of segmentedImage to
        // nextColorToUse
        output.setColor(pc, nextColorToUse);
        // Increment color index
        ++colorIndx;
      }
    }
    return output;
  }

}
