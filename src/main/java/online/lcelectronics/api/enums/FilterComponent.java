package online.lcelectronics.api.enums;

import lombok.Getter;

@Getter
public enum FilterComponent {
    TV_FILTER(Component.MAIN_BOARD, Component.POWER_BOARD, Component.BOARD_WIFI, Component.T_COM_BOARD, Component.DISPLAY_PANEL, Component.LED_BAR, Component.DIFFUSER_SHEET, Component.TUNER, Component.KEYBOARD, Component.ACCESSORIES, Component.SPEAKERS, Component.OTHERS),
    STEREO_FILTER(Component.POWER_BOARD, Component.SPEAKERS, Component.AMPLIFIER_CARD, Component.USB_BLUETOOTH_CARD, Component.WOOFER_SPEAKER, Component.TWEETER_SPEAKER, Component.BAFFLE, Component.DISPLAY_CARD, Component.KEYBOARD, Component.ACCESSORIES, Component.OTHERS),
    MICROWAVE_FILTER(Component.DISPLAY_CARD, Component.MAGNETRON, Component.HIGH_VOLTAGE_TRANSFORMER, Component.FLAT_MOTOR, Component.FAN_MOTOR, Component.DOOR_BUTTON, Component.GATE_SWITCH, Component.REFRACTORY_PLATE, Component.KEYBOARD, Component.ACCESSORIES, Component.OTHERS);

    private final Component[] componentsArray;

    FilterComponent(Component... components) {
        this.componentsArray = components;
    }

}
