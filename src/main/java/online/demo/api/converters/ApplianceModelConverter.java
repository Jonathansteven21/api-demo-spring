package online.demo.api.converters;

import online.demo.api.dto.ApplianceModelDTO;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.enums.ApplianceCategory;
import online.demo.api.enums.Component;
import online.demo.api.enums.FilterComponent;

/**
 * Converter class to convert between ApplianceModel entities and DTOs.
 */
@org.springframework.stereotype.Component
public class ApplianceModelConverter {

    /**
     * Converts an ApplianceModel entity to its corresponding DTO.
     * @param entity The ApplianceModel entity to convert.
     * @return The converted ApplianceModelDTO.
     */
    public ApplianceModelDTO toDto(ApplianceModel entity) {
        ApplianceModelDTO dto = new ApplianceModelDTO();
        dto.setId(entity.getId());
        dto.setModel(entity.getModel());
        dto.setApplianceCategory(entity.getApplianceCategory());
        dto.setBrand(entity.getBrand());
        dto.setYear(entity.getManufactureYear());
        dto.setComponentList(getFilteredComponentList(entity.getApplianceCategory()));
        return dto;
    }

    /**
     * Retrieves the filtered component list based on the ApplianceCategory.
     * @param applianceCategory The ApplianceCategory to determine the filter.
     * @return The filtered Component array.
     */
    private Component[] getFilteredComponentList(ApplianceCategory applianceCategory) {
        if (applianceCategory.name().contains("TV")) {
            return FilterComponent.TV_FILTER.getComponentsArray();
        } else if (applianceCategory.name().contains("STEREO")) {
            return FilterComponent.STEREO_FILTER.getComponentsArray();
        } else if (applianceCategory.name().contains("MICROWAVE")) {
            return FilterComponent.MICROWAVE_FILTER.getComponentsArray();
        } else {
            return Component.values();
        }
    }
}
