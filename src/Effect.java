public class Effect {
    public static Effect[] effects = effects();
    public String effectName;
    public int frames;
    public boolean stackable;
    public final static int second = 1000/DisplayPanel.FRAME_LENGTH;
    public Effect(String effectName, int frames, boolean stackable) {
        this.effectName = effectName;
        this.frames = frames;
        this.stackable = stackable;
    }

    private static Effect[] effects() {
        Effect[] effects = new Effect[10];
        effects[0] = new Effect("Invincibility",10*second, false);
        effects[1] = new Effect("Slippery",10*second, false);
        effects[2] = new Effect("Trapped",3*second, false);
        effects[3] = new Effect("Speed Doubled",30*second, false);
        effects[4] = new Effect("Speed Quadrupled",10*second, false);
        effects[5] = new Effect("Speed Halved",10*second, false);
        effects[6] = new Effect("Bomb Limit Increase",Integer.MAX_VALUE, true);
        effects[7] = new Effect("Bomb Radius Increase",Integer.MAX_VALUE, true);
        effects[8] = new Effect("Max Health Increase",Integer.MAX_VALUE, true);
        effects[9] = new Effect("Healing",second, false);
        return effects;
    }

    @Override
    public String toString() {
        return effectName;
    }
}
