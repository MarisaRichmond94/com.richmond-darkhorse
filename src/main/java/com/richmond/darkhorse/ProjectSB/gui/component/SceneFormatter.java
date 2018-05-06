package com.richmond.darkhorse.ProjectSB.gui.component;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import java.util.List;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ScrollPane;

public interface SceneFormatter {
	
	//FORMATTING TOOLS
	/**
	 * Sets the ColumnConstraints and the RowConstraints for the given GridPane based on the number of desired columns and rows 
	 * @param gridpane - the GridPane layout for the scene
	 * @param columnCount - the desired number of columns that will go into the GridPane
	 * @param rowCount - the desired number of rows that will go into the GridPane
	 */
	public default void setConstraints(GridPane gridpane,int columnCount,int rowCount,int vGap,int hGap,String cssTag) {
		if(vGap != 0) {gridpane.setVgap(vGap);}
		if(hGap != 0) {gridpane.setHgap(hGap);}
		List<ColumnConstraints> columns = new ArrayList<>();
		int columnNum = 0;
		while(columnNum < columnCount) {
			ColumnConstraints newColumn = new ColumnConstraints();
			newColumn.setPercentWidth(100/columnCount);
			columns.add(newColumn);
			columnNum = columnNum+1;
		}
		for(ColumnConstraints column: columns) {
			gridpane.getColumnConstraints().add(column);
		}
		
		List<RowConstraints> rows = new ArrayList<>();
		int rowNum = 0;
		while(rowNum < rowCount) {
			RowConstraints newRow = new RowConstraints();
			newRow.setPercentHeight(100/rowCount);
			rows.add(newRow);
			rowNum = rowNum+1;
		}
		for(RowConstraints row: rows) {
			gridpane.getRowConstraints().add(row);
		}
		gridpane.getStyleClass().add(cssTag);
	}
	
	/**
	 * Places a node in a GridPane (without column or row spanning)
	 * @param gridpane - a GridPane
	 * @param node - a child node
	 * @param columnIndex - the column number
	 * @param rowIndex - the row number
	 * @param hPos - the horizontal position of the node: center, left, or right (default)
	 * @param vPos - the vertical position of the node: center, bottom, top, or baseline (default)
	 */
	public default void placeNode(GridPane gridpane,Node node,int columnIndex,int rowIndex,String hPos,String vPos) {
		gridpane.add(node,columnIndex,rowIndex);
		if(hPos != null) {setHalignment(gridpane,node,hPos);}
		if(vPos != null) {setValignment(gridpane,node,vPos);}
	}
	
	/**
	 * Places a node in a GridPane using column and row spanning
	 * @param gridpane - a GridPane
	 * @param node - a child node
	 * @param columnIndex - the column number
	 * @param rowIndex - the row number
	 * @param columnspan - the number of columns the node will span
	 * @param rowspan - the number of rows the node will span
	 * @param hPos - the horizontal position of the node: center, left, or right (default)
	 * @param vPos - the vertical position of the node: center, bottom, top, or baseline (default)
	 */
	public default void placeNodeSpan(GridPane gridpane,Node node,int columnIndex,int rowIndex,int columnspan,int rowspan,String hPos,String vPos) {
		gridpane.add(node,columnIndex,rowIndex);
		GridPane.setConstraints(node,columnIndex,rowIndex,columnspan,rowspan);
		if(hPos != null) {setHalignment(gridpane,node,hPos);}
		if(vPos != null) {setValignment(gridpane,node,vPos);}
	}
	
	/**
	 * Sets the horizontal alignment for the passed node
	 * @param gridpane - a GridPane
	 * @param node - a child node
	 * @param hPos - the horizontal position of the node: center, left, or right (default)
	 */
	public default void setHalignment(GridPane gridpane,Node node,String hPos) {
		if(hPos.equalsIgnoreCase("center")) {GridPane.setHalignment(node,HPos.CENTER);}
		else if(hPos.equalsIgnoreCase("left")) {GridPane.setHalignment(node,HPos.LEFT);}
		else {GridPane.setHalignment(node,HPos.RIGHT);}
	}
	
	/**
	 * Sets the vertical alignment for the passed node
	 * @param gridpane - a GridPane
	 * @param node - a child node
	 * @param vPos - the vertical position of the node: center, bottom, top, or baseline (default)
	 */
	public default void setValignment(GridPane gridpane,Node node,String vPos) {
		if(vPos.equalsIgnoreCase("center")) {GridPane.setValignment(node,VPos.CENTER);}
		else if(vPos.equalsIgnoreCase("bottom")) {GridPane.setValignment(node,VPos.BOTTOM);}
		else if(vPos.equalsIgnoreCase("top")) {GridPane.setValignment(node,VPos.TOP);}
		else {GridPane.setValignment(node,VPos.BASELINE);}
	}
	
	/**
	 * Sets the different components of the BorderPane layout: center, right, left, top, and bottom
	 * @param borderPane - BorderPane
	 * @param centerPane - center pane (optional)
	 * @param rightPane - right pane (optional)
	 * @param leftPane - left pane (optional)
	 * @param topPane - top pane (optional)
	 * @param bottomPane - bottom pane (optional)
	 */
	public default void setBorderPane(BorderPane borderPane,Pane centerPane,Pane rightPane,Pane leftPane,Pane topPane,Pane bottomPane) {
		if(centerPane != null) {borderPane.setCenter(centerPane);}
		if(rightPane != null) {borderPane.setRight(rightPane);}
		if(leftPane != null) {borderPane.setLeft(leftPane);}
		if(topPane != null) {borderPane.setTop(topPane);}
		if(bottomPane != null) {borderPane.setBottom(bottomPane);}
	}
	
	/**
	 * Sets the different components of the BorderPane layout: center, right, left, top, and bottom
	 * @param borderPane - BorderPane
	 * @param centerPane - center scroll pane (optional)
	 * @param rightPane - right pane (optional)
	 * @param leftPane - left pane (optional)
	 * @param topPane - top pane (optional)
	 * @param bottomPane - bottom pane (optional)
	 */
	public default void setBorderPaneCenterScroll(BorderPane borderPane,ScrollPane centerPane,Pane rightPane,Pane leftPane,Pane topPane,Pane bottomPane) {
		if(centerPane != null) {borderPane.setCenter(centerPane);}
		if(rightPane != null) {borderPane.setRight(rightPane);}
		if(leftPane != null) {borderPane.setLeft(leftPane);}
		if(topPane != null) {borderPane.setTop(topPane);}
		if(bottomPane != null) {borderPane.setBottom(bottomPane);}
	}
	
	/**
	 * Sets the different components of the BorderPane layout: center, right, left, top, and bottom
	 * @param borderPane - BorderPane
	 * @param centerPane - center pane (optional)
	 * @param rightPane - right pane (optional)
	 * @param leftPane - left pane (optional)
	 * @param topPane - top pane (optional)
	 * @param bottomPane - bottom pane (optional)
	 */
	public default void setBorderPaneRightScroll(BorderPane borderPane,Pane centerPane,ScrollPane rightPane,Pane leftPane,Pane topPane,Pane bottomPane) {
		if(centerPane != null) {borderPane.setCenter(centerPane);}
		if(rightPane != null) {borderPane.setRight(rightPane);}
		if(leftPane != null) {borderPane.setLeft(leftPane);}
		if(topPane != null) {borderPane.setTop(topPane);}
		if(bottomPane != null) {borderPane.setBottom(bottomPane);}
	}
	
	//COMPONENT CREATION TOOLS
	/**
	 * Builds a button with a label beneath it
	 * @param button - an ImageButton
	 * @param label - the desired label for the button
	 * @return a centered VBox with the button and the label 
	 */
	public default VBox buildButtonWithLabel(ImageButton button,Label label) {
		VBox labeledButton = new VBox();
		labeledButton.setAlignment(Pos.CENTER);
		labeledButton.getChildren().addAll(button,label);
		return labeledButton;
	}
	
	/**
	 * Creates an Image and puts it in an ImageView
	 * @param imageString - the name of the image file 
	 * @return an ImageView with the desired Image
	 */
	public default ImageView createImage(String imageString) {
		Image image = new Image(imageString);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		return imageView;
	}
	
	/**
	 * Creates an ImageView using the passed image file name and sets the fit height
	 * @param imageString - the name of the image file 
	 * @param fitHeight - the desired height of the image
	 * @return an ImageView with the desired Image
	 */
	public default ImageView createImageWithFitHeight(String imageString,int fitHeight) {
		Image image = new Image(imageString);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(fitHeight);
		return imageView;
	}
	
	/**
	 * Creates a new Label. If labelText is null, throws new NullPointerException
	 * @param labelText - what the label says
	 * @param cssTag - style tag
	 * @return a new label
	 */
	public default Label createLabel(String labelText,String cssTag) throws NullPointerException{
		if(labelText == null) {throw new NullPointerException("label must contain valid text");}
		Label newLabel = new Label (labelText);
		newLabel.getStyleClass().add(cssTag);
		return newLabel;
	}
	
	/**
	 * Creates a new TextField
	 * @param promptText - text hint to user (optional) 
	 * @param cssTag - style tag
	 * @param maxWidth - max width of text field
	 * @return
	 */
	public default TextField createTextField(String promptText,String cssTag,int maxWidth) {
		TextField newTextField = new TextField();
		if(promptText != null) {newTextField.setPromptText(promptText);}
		if(maxWidth != 0) {newTextField.setMaxWidth(maxWidth);}
		if(cssTag != null) {newTextField.getStyleClass().add(cssTag);}
		return newTextField;
	}
	
	/**
	 * Creates a new button
	 * @param text - what the button will say (optional)
	 * @param imageString - image to be displayed on the button (optional)
	 * @param fitHeight - fit height of the image (optional)
	 * @param height - height of the button (optional)
	 * @param width - width of the button (optional)
	 * @return a button
	 */
	public default Button createButton(String text,String imageString,int fitHeight,int height,int width) {
		Button newButton = new Button();
		if(text == null & imageString != null) {newButton = new Button("",createImageWithFitHeight(imageString,fitHeight));}
		else if(imageString != null){newButton = new Button(text,createImageWithFitHeight(imageString,fitHeight));}
		else if(text != null && imageString == null) {
			newButton = new Button();
			newButton.setText(text);
		}
		newButton.getStyleClass().add("button");
		if(height != 0) { newButton.setPrefHeight(height); }
		if(width != 0) { newButton.setPrefWidth(width); }
		return newButton;
	}
	
	public default ToggleButton createToggleButton(String text,boolean isSelected,int maxHeight,int maxWidth,String cssTag) {
		ToggleButton toggleButton = new ToggleButton(text);
		toggleButton.setSelected(isSelected);
		if(maxHeight != 0) {toggleButton.setMaxHeight(maxHeight);}
		if(maxWidth != 0) {toggleButton.setMaxWidth(maxWidth);}
		if(cssTag != null) {toggleButton.getStyleClass().add(cssTag);}
		return toggleButton;
	}
	
	/**
	 * Creates a button without text
	 * @param height - height of the button
	 * @param width - width of the button
	 * @return a button
	 */
	public default Button createButtonWithoutText(int height,int width) {
		Button newButton = new Button();
		newButton.getStyleClass().add("button");
		newButton.setPrefHeight(height);
		newButton.setPrefWidth(width);
		return newButton;
	}
	
	public default Button createButtonWithLabel(Label label,int height,int width) {
		Button newButton = new Button();
		newButton.getStyleClass().add("button");
		newButton.setGraphic(label);
		newButton.setPrefHeight(height);
		newButton.setPrefWidth(width);
		return newButton;
	}
	
	/**
	 * Creates a List of labels
	 * @param labelNames - the text for the labels
	 * @param cssTag - the CSS tag for each label
	 * @return a list of labels 
	 */
	public default List<Label> populateLabels(List<String> labelNames,String cssTag){
		List<Label> labels = new ArrayList<>();
		for(String labelName : labelNames) {
			Label newLabel = createLabel(labelName,cssTag);
			labels.add(newLabel);
		}
		return labels;
	}
	
	/**
	 * Creates a list of text fields
	 * @param textFieldPrompts - the prompt text to go into the text field
	 * @param cssTag - the CSS tag for each text field
	 * @param maxWidth - the desired width of the text field
	 * @return a list of text fields
	 */
	public default List<TextField> populateTextFields(List<String> textFieldPrompts,String cssTag,int maxWidth){
		List<TextField> textFields = new ArrayList<>();
		for(String textFieldPrompt : textFieldPrompts) {
			TextField newTextField = createTextField(textFieldPrompt,cssTag,maxWidth);
			textFields.add(newTextField);
		}
		return textFields;
	}
	
	/**
	 * Builds a CheckBox
	 * @param text - String of text to be displayed beside the CheckBox
	 * @param cssTag 
	 * @return CheckBox
	 */
	public default CheckBox buildCheckBox(String text,String cssTag) {
		CheckBox checkBox = new CheckBox(text);
	    checkBox.getStyleClass().add("checkbox");
	    return checkBox;
	}
	
	/**
	 * Determines which position to place the new @{Center} button
	 * @param gridpane - GridPane
	 * @param button - button representing a @{Center}
	 * @param row 
	 * @param column
	 */
	public default void determinePosition(GridPane gridpane,Button button,int row,int column) {
		int columnIndex;
		if(column == 0) {placeNodeSpan(gridpane,button,2,1,2,1,"center",null);}
		else {
			boolean isIndexEven = isIntEven(column);
			if(isIndexEven == true) {columnIndex = 2;
			}else {columnIndex = 0;}
			placeNodeSpan(gridpane,button,columnIndex,row,2,1,"center",null);
		}
	}
	
	/**
	 * Determines whether or not the given number is even or odd
	 * @param n - the number being analyzed
	 * @return true if the number (n) is even and false if the number (n) is odd
	 */
	public default boolean isIntEven(int n) {
		if( n % 2 == 0) { return true; }
		return false;
	}
	
	/**
	 * Determines whether or not the given double ends in 0
	 * @param rowIndex - double representing the row number
	 * @return true if the double ends in 0 and false if the number does not end in 0
	 */
	public default boolean doesNumberEndInZero(double rowIndex) {
		if (Math.abs(rowIndex - Math.rint(rowIndex)) == 0.5) { return false; }
		return true;
	}
	
}
