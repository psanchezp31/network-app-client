package gui;

import com.sun.management.OperatingSystemMXBean;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import service.CpuUsageService;
import service.CpuUsageServiceLocalImpl;
import service.CpuUsageServiceRandomImpl;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Frame extends JFrame {

    private final CpuUsageService cpuUsageService = new CpuUsageServiceRandomImpl();

    public Frame(final String title) {
        super(title);
        setSystemLookAndFeel();
        final XYDataset dataset = new XYSeriesCollection();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(560, 370);
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);


        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                XYDataset dataSetFromCpuData = createDataSetFromCpuData(cpuUsageService.loadAndGetCpuData());
                chart.getXYPlot().setDataset(dataSetFromCpuData);
            }
        });
        thread.start();

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                    .getOperatingSystemMXBean();
            Frame window = new Frame("CPU Usage");
            window.setSize(800,600);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTextArea ipTextArea = new JTextArea();
            JButton connectButton = new JButton("Connect");
        });
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "CPU Usage",
                "Time (seconds)",
                "% Utilization",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("CPU Usage",
                        new Font("Sans-Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }

    private XYDataset createDataSetFromCpuData(LinkedList<Double> cpuData) {

        XYSeries series = new XYSeries("CPU Usage");

        for (int i = 0; i < CpuUsageServiceLocalImpl.MAX_COORDINATES_AMOUNT; i++) {
            series.add(i, cpuData.get(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;

    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
