module Clapper {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
  requires java.sql;
requires jfxrt;
requires rt;
	
	opens application to javafx.graphics, javafx.fxml;
	exports controllers;
	opens controllers to javafx.fxml;
}
