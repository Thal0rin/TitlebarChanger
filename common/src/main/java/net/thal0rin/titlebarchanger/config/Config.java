package net.thal0rin.titlebarchanger.config;

public class Config {
    private int theme = 1;
    private int corner = 1;
    private final TitleBarColor titleBarColor = new TitleBarColor();
    private final TitleBarTextColor titleBarTextColor = new TitleBarTextColor();
    private final TitleBarStrokeColor titleBarStrokeColor = new TitleBarStrokeColor();
    private boolean showTheMenu = true;
    private boolean showWarnScreen = true;

    public int getCorner() {
        return this.corner;
    }

    public int getTheme() {
        return this.theme;
    }

    public TitleBarColor getTitleBarColor() {
        return this.titleBarColor;
    }

    public TitleBarTextColor getTitleBarTextColor() {
        return this.titleBarTextColor;
    }

    public TitleBarStrokeColor getTitleBarStrokeColor() {
        return this.titleBarStrokeColor;
    }

    public boolean getShowTheMenu() {
        return this.showTheMenu;
    }

    public boolean getShowWarnScreen() {
        return this.showWarnScreen;
    }

    public void setCorner(int corner) {
        this.corner = corner;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setShowWarnScreen(boolean showWarnScreen) {
        this.showWarnScreen = showWarnScreen;
    }

    public static class TitleBarColor {
        private int r = 5;
        private int g = 5;
        private int b = 5;

        public int getR() {
            return this.r;
        }

        public int getB() {
            return this.b;
        }

        public int getG() {
            return this.g;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setR(int r) {
            this.r = r;
        }
    }

    public static class TitleBarTextColor {
        private int r = 0;
        private int g = 255;
        private int b = 0;

        public int getR() {
            return this.r;
        }

        public int getB() {
            return this.b;
        }

        public int getG() {
            return this.g;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setR(int r) {
            this.r = r;
        }
    }

    public static class TitleBarStrokeColor {
        private int r = 0;
        private int g = 255;
        private int b = 0;

        public int getR() {
            return this.r;
        }

        public int getB() {
            return this.b;
        }

        public int getG() {
            return this.g;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setG(int g) {
            this.g = g;
        }

        public void setR(int r) {
            this.r = r;
        }
    }
}