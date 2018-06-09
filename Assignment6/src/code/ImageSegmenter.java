package code;

import java.awt.Color;
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
    
    // TODO: IMPLEMENT THIS
    
    
    return output;
  }

  /**
   * This function iterates over the image and colors output image using "color"
   * array in circular fashion. i.e. if all colors have been used then pick the
   * first color.
   * 
   * This is given to you as an example of how to use some of the available
   * classes,
   * 
   * @param graph
   *          - image graph.
   * @param output
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
