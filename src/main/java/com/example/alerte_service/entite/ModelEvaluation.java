package com.example.alerte_service.entite;

public class ModelEvaluation {
    private double accuracy;
    private ConfusionMatrix confusionMatrix;
    private double f1Score;
    private double precision;
    private double recall;
    private double rocAuc;

    // Ajoutez les getters et les setters appropriés ici

    public static class ConfusionMatrix {
        private PredictionClass pred0;
        private PredictionClass pred1;

        // Ajoutez les getters et les setters appropriés ici
    }

    public static class PredictionClass {
        private int true0;
        private int true1;

        // Ajoutez les getters et les setters appropriés ici
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public ConfusionMatrix getConfusionMatrix() {
        return confusionMatrix;
    }

    public void setConfusionMatrix(ConfusionMatrix confusionMatrix) {
        this.confusionMatrix = confusionMatrix;
    }

    public double getF1Score() {
        return f1Score;
    }

    public void setF1Score(double f1Score) {
        this.f1Score = f1Score;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getRocAuc() {
        return rocAuc;
    }

    public void setRocAuc(double rocAuc) {
        this.rocAuc = rocAuc;
    }
}

