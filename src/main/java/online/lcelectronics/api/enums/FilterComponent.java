package online.lcelectronics.api.enums;

import lombok.Getter;

@Getter
public enum FilterComponent {
    TV_FILTER(Component.ACCESSORIES, Component.BOARD_WIFI, Component.DIFFUSER_SHEET, Component.DISPLAY_PANEL, Component.KEYBOARD, Component.LED_BAR, Component.MAIN_BOARD, Component.OTHERS, Component.POWER_BOARD, Component.SPEAKERS, Component.T_COM_BOARD, Component.TUNER),
    STEREO_FILTER(Component.ACCESSORIES, Component.AMPLIFIER_CARD, Component.BAFFLE, Component.DISPLAY_CARD, Component.KEYBOARD, Component.OTHERS, Component.POWER_BOARD, Component.SPEAKERS, Component.TWEETER_SPEAKER, Component.USB_BLUETOOTH_CARD, Component.WOOFER_SPEAKER),
    MICROWAVE_FILTER(Component.ACCESSORIES, Component.DISPLAY_CARD, Component.DOOR_BUTTON, Component.FAN_MOTOR, Component.FLAT_MOTOR, Component.GATE_SWITCH, Component.HIGH_VOLTAGE_TRANSFORMER, Component.KEYBOARD, Component.MAGNETRON, Component.OTHERS, Component.REFRACTORY_PLATE);

    private final Component[] componentsArray;

    FilterComponent(Component... components) {
        this.componentsArray = components;
    }

}
