public class MazeTile {
  private int x;
  private int y;
  private char type; // 'E': Empty, 'W':Wall, 'T': Trap, 'P': Powerup, 'G': Goal
  private boolean hasAgent;

  public MazeTile(int x, int y, char type) {
    this.x = x;
    this.y = y;
    this.type = type;
    this.hasAgent = false;
  }

  public char getType() {
    return type;
  }

  public void setType(char type) {
    this.type = type;
  }

  public void setHasAgent(boolean hasAgent) {
    this.hasAgent = hasAgent;
  }

  public boolean isTraversable(){
    switch (type) {
      case 'E':
      case 'P':
      case 'G':
        return true; // Empty, Powerup, and Goal tiles are traversable
      case 'W':
      case 'T':
        return false; // Wall and Trap tiles are not traversable
      default:
        return false; // Unknown tile type
    }
  }

  public String toString(){
    if (hasAgent) {
      return "A";
    }
    switch (type) {
      case 'E':
        return " ";
      case 'W':
        return "#";
      case 'T':
        return "T";
      case 'P':
        return "P";
      case 'G':
        return "X";
      default:
        return " ";
    }
  }
  
}
