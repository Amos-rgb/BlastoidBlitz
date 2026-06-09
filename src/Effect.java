public class Effect {
    public static Effect[] effects = effects();
    public String effectName;
    public int frames;
    public boolean stackable;
    public Effect(String effectName, int frames, boolean stackable) {
        this.effectName = effectName;
        this.frames = frames;
        this.stackable = stackable;
    }

    private static Effect[] effects() {
        int second = 1000/DisplayPanel.FRAME_LENGTH;
        Effect[] effects = new Effect[10];
        effects[0] = new Effect("Immunity",3*second, false);
        effects[1] = new Effect("Slippery",10*second, false);
        effects[2] = new Effect("Trapped",3*second, false);
        effects[3] = new Effect("Minor Speed Increase",10*second, false);
        effects[4] = new Effect("Major Speed Increase",3*second, false);
        effects[5] = new Effect("Speed Decrease",10*second, false);
        effects[6] = new Effect("Bomb Limit Increase",second, true);
        effects[7] = new Effect("Bomb Radius Increase",second, true);
        effects[8] = new Effect("Max Health Increase",second, true);
        effects[9] = new Effect("Healing",1, false);
        return effects;
    }

    @Override
    public String toString() {
        return effectName;
    }
}
