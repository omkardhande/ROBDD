

public class Node 
{
	int low;
	int high;
	int index;
	boolean terminalVal;
	
    public Node(boolean value, int numInputs)
    {
        this.low = -1;
        this.high = -1;
        this.index = numInputs;
        this.terminalVal = value;
    }
    
    public Node(int lowChild, int highChild, int index)
    {
        this.low = lowChild;
        this.high = highChild;
        this.index = index;
    }

    public Node(Node n)
    {
        this.low = n.low;
        this.high = n.high;
        this.index = n.index;
        this.terminalVal = n.terminalVal;
    }
    
    public boolean isTerminal()
    {
        return ((low == -1 && high == -1) ? true : false);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + this.low;
        hash = 29 * hash + this.high;
        hash = 29 * hash + this.index;
        hash = 29 * hash + (this.terminalVal ? 1 : 0);
        return hash;
    }
    @Override
    public boolean equals(Object n)
    {
        if (! (n instanceof Node))
            return false;
        return ((this.low == ((Node)n).low) && (this.high == ((Node)n).high) && (this.index == ((Node)n).index) && (this.terminalVal == ((Node)n).terminalVal));
    }
}
