package knapsack.components;

import javafx.geometry.Point3D;

import java.util.*;

/**
 * Class used to define the truck where the parcels or pentominoes are going to be placed.
 */
public class Truck {
	public ArrayList<Parcel> parcelList;
	private int length;
	private int height;
	private int width;
	private String[][][] truck;

	/**
	 *Create a parcel with a default position set to the origin and set its 8 vertices.
     *Object constructor.
     *
     * @param l The length of the Truck.
     * @param h The height of the Truck.
     * @param w The width of the Truck
	 */
	public Truck(double w, double h, double l) {
	    parcelList = new ArrayList<>();
		length = (int) (l/0.5);
		height = (int) (h/0.5);
		width = (int) (w/0.5);
		truck = new String[width][height][length];
		for(int i = 0; i<width ; i++){
		    for(int j=0; j<height; j++){
		        for(int k=0; k<length; k++){
		            truck[i][j][k] = "-";
                }
            }
        }
	}

    /**
     * Default constructor for this project. Truck with size: 16.5 x 4.0 x 2.5 .
     */
    public Truck(){
        this(16.5,4.0,2.5);
    }

    public Truck(String[][][] truck){
    	this();
    	this.truck = truck;
	}

	public String[][][] getTruck(){
		return truck;
	}
	
	public void setTruck(String[][][] newTruck) {
		for(int i=0; i<truck.length; i++) {
			for(int j=0; j<truck[0].length; j++) {
				for(int k=0; k<truck[0][0].length; k++) {
					truck[i][j][k]=newTruck[i][j][k];
				}
			}
		}
	}

	public void setTruck(int x, int y, int z, String str) {
		truck[x][y][z]=str;
	}

	public int[] positionToAdd() {
		int[] position = new int[3];
		
		for(int i=0; i<truck[0].length; i++) {
			for(int j=0; j<truck.length; j++) {
				for(int k=0; k<truck[0][0].length; k++) {
					if(truck[j][i][k].equals("-")) {
						position[0]=j;
						position[1]=i;
						position[2]=k;
						System.out.println("Position to ADd"+position[0]+" "+position[1]+" "+position[2]);
						return position;
					}
				}
			}
		}
		return position;
		
	}

    /**
     * Default constructor for this project. Truck with size: 16.5 x 4.0 x 2.5 .
     */
	public Truck(){
	    this(4.0,2.5,4.0);
    }

    /**
     * Method to get the length of the truck. (z axis)
     * @return The length of the truck.
     */
	public int getLength() { return length; }
    /**
     * Method to get the height of the truck. (y axis)
     * @return The height of the truck.
     */

	public int getWidth()  { return width;  }
    /**
     * Method to get the width of the truck. (x axis)
     * @return The width of the truck.
     */
    public int getHeight() { return height; }

    /**
     * Method to get the volume of the truck
     * @return The volume of the truck.
     */
    public int getVolume() {return height*width*length;}
    
    /**
     * Method to add a parcel object to the truck.
     * @param p The parcel to be added to the truck.
     */
    public boolean addParcel(Parcel p){
        for(int i = 0; i<width-(p.getWidth()*2) ; i++){
            for(int j=0; j<height-(p.getHeight()*2); j++){
                for(int k=0; k<length-(p.getLength()*2); k++){

                    if(isPossible(p,new Point3D(i,j,k))){
                        parcelList.add(p);
                        for(int a=0; a<p.getWidth()*2; a++){
                            for(int b=0; b<p.getHeight()*2; b++){
                                for(int c=0; c<p.getLength()*2;c++){
                                    truck[a+i][b+j][c+k] = p.getID();
                                }
                            }
                        }
                        p.setPos(new Point3D(i,j,k));
                        return true;
                    }
                }
            }
        }
        return false;
	}

	public boolean isPossible(Parcel p, Point3D pos) {

		if(pos.getX()+p.getWidth()>truck.length || pos.getY()+p.getHeight()>truck[0].length ||
				pos.getZ()+p.getLength()>truck[0][0].length)
			return false;


		for (int i = 0; i < p.getWidth(); i++) {
			for (int j = 0; j < p.getHeight(); j++) {
				for (int k = 0; k < p.getLength(); k++) {

					 if (!truck[i + (int) pos.getX()][j + (int) pos.getY()][k + (int) pos.getZ()].equals("-"))
						return false;

				}
			}
		}

		return true;
	}

    /**
     * Method to remove a parcel from the truck.
     * @param p The parcel to be removed.
     */
	public void removeParcel(Parcel p){
	    parcelList.remove(p);
        Point3D pos = p.getPos();
        int x = (int) (pos.getX()/0.5);
        int y = (int) (pos.getY()/0.5);
        int z = (int) (pos.getZ()/0.5);
        for(int i=0; i<p.getWidth(); i++){
            for(int j=0; j<p.getHeight(); j++){
                for(int k=0; k<p.getLength();k++){
                    truck[x+i][y+j][z+k] = "-";
                }
            }
        }
    }

	public String[][][] copyTruck(){
		String[][][] newTruck = new String[truck.length][truck[0].length][truck[0][0].length];
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				for(int k=0; k<length; k++) {
					newTruck[i][j][k]=truck[i][j][k];
				}
			}
		}
		return new Truck(newTruck);
	}

	public void printTruck() {
		System.out.println("NEW TRUCK");
		for(int i=0; i<truck[0][0].length; i++) {
			for(int j=0; j<truck.length; j++) {
				for(int k=0; k<truck[0].length; k++) {
					System.out.print(truck[j][k][i]+ " ");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}

	public int getGapAmount() {
	    int sum = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < length; k++) {
                    if(truck[i][j][k].equals("-")) sum++;
                }
            }
        }
        return sum;
    }

    public boolean fillTruck(ArrayList<Parcel> list, int index, Truck t){
	    if(getVolume()*0.9 > getVolume()-getGapAmount() || list.isEmpty()) return true;
	    else {
            if(addParcel(list.get(index))){
                ArrayList<Parcel> newList = new ArrayList<>();
                for(int i=0; i<list.size(); i++) newList.add(list.get(i));
                newList.remove(index);
                fillTruck(list, index, t.copyTruck());
            }
            else {

			}
        }
        return false;
    }
}
