public class Effect {
    public static Effect[] effects = effects();
    public String effectName;
    public int frames;
    public Effect(String effectName, int frames) {
        this.effectName = effectName;
        this.frames = frames;
    }

    private static Effect[] effects() {
        int second = 1000/DisplayPanel.FRAME_LENGTH;
        Effect[] effects = new Effect[10];
        effects[0] = new Effect("Immunity",3*second);
        effects[1] = new Effect("Slippery",10*second);
        effects[2] = new Effect("Trapped",3*second);
        effects[3] = new Effect("Bomb Limit Increase",30);
        effects[4] = new Effect("Bomb Radius Increase",3000);
        effects[5] = new Effect("Max Health Increase",3000);
        effects[6] = new Effect("Minor Speed Increase",3000);
        effects[7] = new Effect("Major Speed Increase",3000);
        effects[8] = new Effect("Speed Decrease",3000);
        effects[9] = new Effect("Healing",3000);
        return effects;
    }
}
