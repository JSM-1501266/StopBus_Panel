package panel.view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import panel.MainApp;
import panel.model.ArrivingBus;
import panel.model.BusInfo;
import panel.model.BusStop;

public class PanelOverviewController {

	@FXML
	private VBox arrivingBusBox = new VBox(5);

	@FXML
	private Label busStopNumLabel;
	@FXML
	private Label busStopNameLabel;
	@FXML
	private Label busStopInfoLabel;
	
	@FXML
	private Pane testPane;
	
	@FXML
	private Button searching;
	
	@FXML
	private Button checking;
	

	private ObservableList<ArrivingBus> arrivingBusData = FXCollections.observableArrayList();
	
	
	private MainApp mainApp;
	
	public PanelOverviewController() {
	}
	
	@FXML
	private void setVisible() {
		testPane.setVisible(true);
		mainApp.setPaginationUnvisible();
	}
	
	@FXML
	private void setUnvisible() {
		testPane.setVisible(false);
		mainApp.setPaginationVisible();
	}
	
	@FXML
	private void handleSettingBusStop() {
		BusStop tempBusStop = new BusStop();
		boolean okClicked = mainApp.showSettingDialog(tempBusStop);
		if(okClicked) {
			//mainApp.getBusStop().setBusStopNum(tempBusStop.getBusStopNum());
			//mainApp.getBusStop().setBusStopName(tempBusStop.getBusStopName());
			//mainApp.getBusStop().setBusStopInfo(tempBusStop.getBusStopInfo());
		}
		busStopNumLabel.setText(mainApp.getBusStop().getBusStopNum());
		busStopNameLabel.setText(mainApp.getBusStop().getBusStopName());
		busStopInfoLabel.setText(mainApp.getBusStop().getBusStopInfo());
	}
	
	@FXML
	private void initialize() {
		
		/*
		busNumberColumn.setCellValueFactory(cellData -> cellData.getValue().busNumberProperty());
		timeRemainingColumn.setCellValueFactory(cellData -> cellData.getValue().timeRemainingProperty());
		currentStopColumn.setCellValueFactory(cellData -> cellData.getValue().currentStopProperty());

		busNumberColumn.setCellFactory(column -> {
			return new TableCell<ArrivingBus, String>(){
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					
					setText(empty ? "" : getItem().toString());
					setGraphic(null);
					
					TableRow<ArrivingBus> currentRow = getTableRow();
					ArrivingBus rowData = currentRow.getItem();
					
					//add delay
					currentRow.setOnMouseClicked(event-> {
						if(event.getClickCount() == 2 && (! currentRow.isEmpty())) {
							rowData.setAvailability(1);
						}
					});

					
					if(!isEmpty()) {
						if(rowData.getAvailability() != 0) {
							currentRow.setStyle("-fx-background-color:lightcoral");
						}
					}
					
					arrivingBusTable.refresh();
				}
			};
		});
		*/
      
	}
	
	public void createArrivingBusBox() {
		
		for (int i = 0; i < arrivingBusData.size(); i++) {
			ArrivingBus tempArrivingBus = arrivingBusData.get(i);
					
        	HBox hbox = new HBox();
        	//hbox.setStyle("-fx-border-color: #F9F9F9");
        	hbox.setStyle("-fx-background-color:white");
        	hbox.setAlignment(Pos.CENTER);
        	
        	Label busNum = new Label(tempArrivingBus.getBusNumber()+" 번");
    		Label busTime = new Label(tempArrivingBus.getTimeRemaining()+" 분 전");
    		Label busStop = new Label(tempArrivingBus.getCurrentStop()+" 정거장 전");
    		
    		busNum.setFont(new Font("Arial", 16));
    		busNum.setStyle("-fx-padding: 10;");
    		busNum.setMinWidth(160.0);
    		busTime.setFont(new Font("Arial", 16));
    		busTime.setStyle("-fx-padding: 10;");
    		busTime.setMinWidth(160.0);
    		busStop.setFont(new Font("Arial", 16));
    		busStop.setStyle("-fx-padding: 10;");
    		busStop.setMinWidth(160.0);
    		
    		hbox.getChildren().add(busNum);
    		hbox.getChildren().add(busTime);
    		hbox.getChildren().add(busStop);
            
    		arrivingBusBox.getChildren().add(hbox);
			
        }
	}
	
	private void boxifyBoxes() {
		
		for(Node child : arrivingBusBox.getChildren()) {
			HBox hb = (HBox) child;
			hb.setOnMouseClicked((e) -> {
				hb.setStyle("-fx-background-color:lightcoral");
				setAvailability((int) hb.getLayoutY());
			});

		}
	}
	
	private void updateBoxes() {
		
		int index;
		for(Node child : arrivingBusBox.getChildren()) {
			HBox hb = (HBox) child;
			index = (int) hb.getLayoutY() / 40;
			if(arrivingBusData.get(index).getAvailability() != 0) {
				hb.setStyle("-fx-background-color:lightcoral");
			}
		}
	}

	
	private void setAvailability(int i) {
		int index = i / 40;
		arrivingBusData.get(index).setAvailability(1);
		for(int j = 0; j < mainApp.getBusInfoListData().size(); j++) {
			if(arrivingBusData.get(index).getBusNumber() == mainApp.getBusInfoListData().get(j).getBusNum()) {
				mainApp.getBusInfoListData().get(j).setAvailability(1);
			}
		}
	}

	@FXML
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		busStopNumLabel.setText(mainApp.getBusStop().getBusStopNum());
		busStopNameLabel.setText(mainApp.getBusStop().getBusStopName());
		busStopInfoLabel.setText(mainApp.getBusStop().getBusStopInfo());
		arrivingBusData = mainApp.getArrivingBusData();
		
		createArrivingBusBox();
		boxifyBoxes();
	}

}
