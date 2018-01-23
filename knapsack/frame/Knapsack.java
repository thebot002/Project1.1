package knapsack.frame;

import knapsack.components.AlgorithmInfo;
import knapsack.components.Parcel;
import knapsack.components.Truck;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Knapsack extends JFrame {
    private Truck truck;

    public static void main(String[] args) {
        new Knapsack();
    }

    private CubeDrawer c;
    private Menu m;
	private Parcel[] parcelsA;
	private Parcel[] parcelsB;
	private Parcel[] parcelsC;
	private double maximum;
	private double medium;
	private double minimum;
	private Parcel parcelA = new Parcel("A");
	private Parcel parcelB = new Parcel("B");
	private Parcel parcelC = new Parcel("C");

    public Knapsack() {
        setTitle("Knapsack");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyInput();
        addComponentListener(new ResizeListener());


        truck = new Truck();

        Color scene_BACKGROUND = Color.BLACK;   //Background of scene
        Color scene_FOREGROUND = Color.WHITE;   //Wireframe color of scene
        Color cube_FOREGROUND = Color.YELLOW;   //Cube Wireframe color


        truck.setBackground(scene_BACKGROUND);
        truck.setForeground(scene_FOREGROUND);
        truck.setCubeColor(cube_FOREGROUND);

        c = new CubeDrawer(800, 600, truck);

        m = new Menu(this);
        m.setCubeDrawer(c);

        add(m, BorderLayout.EAST);
        add(c, BorderLayout.CENTER);
        pack();
        setVisible(true);
        //setResizable(false);
        c.renderScene();
        addComponentListener(new ResizeListener());
    }


    public class ResizeListener implements ComponentListener {

        @Override
        public void componentResized(ComponentEvent e) {
            c.doResize(getWidth() - m.getWidth() - 16, c.getHeight());
        }

        @Override
        public void componentMoved(ComponentEvent e) {}
        @Override
        public void componentShown(ComponentEvent e) { }
        @Override
        public void componentHidden(ComponentEvent e) { }
    }

    private void addKeyInput() {
        KeyboardFocusManager keyManager;
        keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyManager.addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getID()==KeyEvent.KEY_PRESSED ) {
                    int key = e.getKeyCode();

                    CubeDrawer.Camera cam = c.getCamera();

                    if (key == 40)  //down arrow
                        cam.roll(-3);

                    if (key == 38)  //up arrow
                        cam.roll(3);

                    if (key == 39)  //right arrow
                        cam.rotate(3);

                    if (key == 37)  //left arrow
                        cam.rotate(-3);

                    if (key == 45 || key == 109)  //minus
                        cam.zoom(-1);

                    if (key == 61 || key == 107)  //plus
                        cam.zoom(1);
                    if (key == 68)  // d
                        c.toggleDebug();

                    if (key == 80)  // p
                        c.toggleCoodDrawing();

                    if (key == 70)  // f
                        c.toggleFill();

					if (key == 73)  // i
						c.toggleColor();

                    m.getInfoTab().setElevation(cam.elevation);
                    m.getInfoTab().setElevation(cam.elevation);
                    m.getInfoTab().setAngle(cam.angle);
                    m.getInfoTab().setZoom(cam.scale);
                }
                return false;
            }
        });
    }

    public void fill(AlgorithmInfo info) {
    	Parcel[] parcelArr = createInputParcelArr(info);
    	//do something interface implementation stuff idk
    }

	public CubeDrawer getCubeDrawer() {
		return c;
	}
    private Parcel[] createInputParcelArr(AlgorithmInfo info) {
		int lengthCounter = 0;
		int indexEmpty = 0;
		int indexTMP = 0;
		int[][] parcelsArr = info.getParcels();
		String[] settings = info.getSettings();
		if(parcelsArr[0][0] != 0) {
			parcelsA = Parcel.createParcelsArrA(parcelsArr[0][1]);
			lengthCounter += parcelsA.length;
		}
		if(parcelsArr[1][0] != 0) {
			parcelsB = Parcel.createParcelsArrB(parcelsArr[1][1]);
			lengthCounter += parcelsB.length;
		}
		if(parcelsArr[2][0] != 0) {
			parcelsC = Parcel.createParcelsArrC(parcelsArr[2][1]);
			lengthCounter += parcelsC.length;
		}
		Parcel[] inputParcelArr = new Parcel[lengthCounter];
		if(settings[1].equals("Decreasing value/volume")) { // {"Decreasing value/volume", "Decreasing value", "Decreasing volume", "Genetic Algorithm"}
			sortValueOverVolume(parcelsArr);
			if(maximum == parcelsArr[0][1]) {
				if(parcelsArr[0][0] != 0) {
					for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsA[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP; 
					if(medium == parcelsArr[1][1]) {
						if(parcelsArr[1][0] != 0) {
							for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsB[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[2][1]) {
								if(parcelsArr[2][0] != 0) {
									for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsC[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelsArr[2][1]) {
						if(parcelsArr[2][0] != 0) {
							for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsC[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[1][1]) {
								if(parcelsArr[1][0] != 0) {
									for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsB[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
			
			if(maximum == parcelsArr[1][1]) {
				if(parcelsArr[1][0] != 0) {
					for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsB[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					indexEmpty = indexTMP;
					if(medium == parcelsArr[0][1]) {
						if(parcelsArr[0][0] != 0) {
							for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsA[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[2][1]) {
								if(parcelsArr[2][0] != 0) {
									for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsC[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelsArr[2][1]) {
						if(parcelsArr[2][0] != 0) {
							for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsC[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[0][1]) {
								if(parcelsArr[0][0] != 0) {
									for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsA[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
			if(maximum == parcelsArr[2][1]) {
				if(parcelsArr[2][0] != 0) {
					for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsC[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelsArr[0][1]) {
						if(parcelsArr[0][0] != 0) {
							for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsA[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[1][1]) {
								if(parcelsArr[1][0] != 0) {
									for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsB[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelsArr[1][1]) {
						if(parcelsArr[1][0] != 0) {
							for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsB[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[0][1]) {
								if(parcelsArr[0][0] != 0) {
									for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsA[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
		}
		if(settings[1].equals("Decreasing value")) { // {"Decreasing value/volume", "Decreasing value", "Decreasing volume", "Genetic Algorithm"}
			sortValue(parcelsArr);
			if(maximum == parcelsArr[0][1]) {
				if(parcelsArr[0][0] != 0) {
					for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsA[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelsArr[1][1]) {
						if(parcelsArr[1][0] != 0) {
							for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsB[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[2][1]) {
								if(parcelsArr[2][0] != 0) {
									for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsC[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelsArr[2][1]) {
						if(parcelsArr[2][0] != 0) {
							for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsC[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[1][1]) {
								if(parcelsArr[1][0] != 0) {
									for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsB[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
			
			if(maximum == parcelsArr[1][1]) {
				if(parcelsArr[1][0] != 0) {
					for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsB[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelsArr[0][1]) {
						if(parcelsArr[0][0] != 0) {
							for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsA[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[2][1]) {
								if(parcelsArr[2][0] != 0) {
									for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsC[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelsArr[2][1]) {
						if(parcelsArr[2][0] != 0) {
							for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsC[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[0][1]) {
								if(parcelsArr[0][0] != 0) {
									for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsA[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
			if(maximum == parcelsArr[2][1]) {
				if(parcelsArr[2][0] != 0) {
					for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsC[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelsArr[0][1]) {
						if(parcelsArr[0][0] != 0) {
							for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsA[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[1][1]) {
								if(parcelsArr[1][0] != 0) {
									for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsB[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelsArr[1][1]) {
						if(parcelsArr[1][0] != 0) {
							for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsB[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelsArr[0][1]) {
								if(parcelsArr[0][0] != 0) {
									for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsA[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
		}
		if(settings[1].equals("Decreasing volume")) { // {"Decreasing value/volume", "Decreasing value", "Decreasing volume", "Genetic Algorithm"}
			sortVolume(parcelsArr);
			if(maximum == parcelA.getVolume()) {
				if(parcelsArr[0][0] != 0) {
					for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsA[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelB.getVolume()) {
						if(parcelsArr[1][0] != 0) {
							for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsB[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelC.getVolume()) {
								if(parcelsArr[2][0] != 0) {
									for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsC[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelC.getVolume()) {
						if(parcelsArr[2][0] != 0) {
							for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsC[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelB.getVolume()) {
								if(parcelsArr[1][0] != 0) {
									for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsB[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
			
			if(maximum == parcelB.getVolume()) {
				if(parcelsArr[1][0] != 0) {
					for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsB[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelA.getVolume()) {
						if(parcelsArr[0][0] != 0) {
							for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsA[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelC.getVolume()) {
								if(parcelsArr[2][0] != 0) {
									for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsC[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelC.getVolume()) {
						if(parcelsArr[2][0] != 0) {
							for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsC[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelA.getVolume()) {
								if(parcelsArr[0][0] != 0) {
									for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsA[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
			if(maximum == parcelC.getVolume()) {
				if(parcelsArr[2][0] != 0) {
					for(int i = indexEmpty; i < parcelsC.length + indexEmpty; i++) {
						inputParcelArr[i] = parcelsC[i - indexEmpty];
						indexTMP = i + 1;
					}
					indexEmpty = indexTMP;
					if(medium == parcelA.getVolume()) {
						if(parcelsArr[0][0] != 0) {
							for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsA[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							indexEmpty = indexTMP;
							if(minimum == parcelB.getVolume()) {
								if(parcelsArr[1][0] != 0) {
									for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsB[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
					if(medium == parcelB.getVolume()) {
						if(parcelsArr[1][0] != 0) {
							for(int i = indexEmpty; i < parcelsB.length + indexEmpty; i++) {
								inputParcelArr[i] = parcelsB[i - indexEmpty];
								indexTMP = i + 1;
							}
							indexEmpty = indexTMP;
							if(minimum == parcelA.getVolume()) {
								if(parcelsArr[0][0] != 0) {
									for(int i = indexEmpty; i < parcelsA.length + indexEmpty; i++) {
										inputParcelArr[i] = parcelsA[i - indexEmpty];
										indexTMP = i + 1;
									}
									indexEmpty = indexTMP;
								}
							}
						}
					}
				}
			}
		}
		if(settings[1].equals("Genetic Algorithm - Minimize Gaps")) {
			//depends on implementation of GA
		}
		if(settings[1].equals("Genetic Algorithm - Maximize Volume")) {
			//depends on implementation of GA
		}
		return inputParcelArr;
	}
	private double[] sortVolume(int[][] parcelsArr) {
		double[] sortVolume = {parcelA.getVolume(), parcelB.getVolume(), parcelC.getVolume()};
		Arrays.sort(sortVolume);
		for(int i = 0; i < sortVolume.length; i++) {
			maximum = sortVolume[0];
			medium = sortVolume[1];
			minimum = sortVolume[2];
		}
		return sortVolume;
	}
	private double[] sortValue(int[][] parcelsArr) {
		double[] sortValue = {parcelsArr[0][1], parcelsArr[1][1], parcelsArr[2][1]};
		Arrays.sort(sortValue);
		for(int i = 0; i < sortValue.length; i++) {
			maximum = sortValue[0];
			medium = sortValue[1];
			minimum = sortValue[2];
		}
		return sortValue;
	}
	private double[] sortValueOverVolume(int[][] parcelsArr) {
		double[] sortValueOverVolume = {parcelsArr[0][1], parcelsArr[1][1], parcelsArr[2][1]};
		Arrays.sort(sortValueOverVolume);
		for(int i = 0; i < sortValueOverVolume.length; i++) {
			maximum = sortValueOverVolume[0];
			medium = sortValueOverVolume[1];
			minimum = sortValueOverVolume[2];
		}
		return sortValueOverVolume;
	}

}
