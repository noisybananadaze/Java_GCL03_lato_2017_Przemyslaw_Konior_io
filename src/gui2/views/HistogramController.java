package gui2.views;

import gui2.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class HistogramController {

    @FXML private BarChart<String, Integer> barChart;
    @FXML private CategoryAxis xAxis;

    public void initialize() {
        ObservableList<String> marks = FXCollections.observableArrayList("2.0", "3.0", "4.0", "5.0");
        xAxis.setCategories(marks);
//        ObservableList<Student> students
        setStudentsData();
    }

    public void setStudentsData() {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        int mark2 = 0;
        int mark3 = 0;
        int mark4 = 0;
        int mark5 = 0;
//        for (Student s : students) {
//            if (s.getMark() == 2.0) {mark2++;}
//            if (s.getMark() == 3.0) {mark2++;}
//            if (s.getMark() == 4.0) {mark2++;}
//            if (s.getMark() == 5.0) {mark2++;}
//        }


        series.getData().add(new XYChart.Data<>("2.0", 3));
        series.getData().add(new XYChart.Data<>("3.0", 7));
        series.getData().add(new XYChart.Data<>("4.0", 12));
        series.getData().add(new XYChart.Data<>("5.0", 6));

        barChart.getData().add(series);
    }
}
