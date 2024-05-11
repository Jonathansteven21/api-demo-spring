package online.lcelectronics.api.services;

import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.converters.ApplianceModelConverter;
import online.lcelectronics.api.dto.ApplianceModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.repositories.ApplianceModelRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplianceModelService {

    private final ApplianceModelRepository applianceModelRepository;
    private final ApplianceModelConverter applianceModelConverter;

    // Retrieve all appliance models
    public List<ApplianceModel> getAllApplianceModels() {
        return applianceModelRepository.findAll();
    }

    // Retrieve an appliance model by its ID
    public ApplianceModelDTO getApplianceModelById(Integer id) {
        Optional<ApplianceModel> optionalApplianceModel = applianceModelRepository.findById(id);
        return optionalApplianceModel.map(applianceModelConverter::toDto).orElse(null);
    }

    // Retrieve an appliance model by its model
    public ApplianceModelDTO getApplianceModelByModel(String model) {
        Optional<ApplianceModel> optionalApplianceModel = applianceModelRepository.findByModel(model);
        return optionalApplianceModel.map(applianceModelConverter::toDto).orElse(null);
    }

    // Retrieve appliance models whose model contains the specified substring
    public List<ApplianceModel> getApplianceModelsByModelContaining(String model) {
        return applianceModelRepository.findByModelContaining(model);
    }

    // Retrieve appliance models by their category and brand
    public List<ApplianceModel> getApplianceModelsByApplianceCategoryAndBrand(ApplianceCategory category, Brand brand) {
        return applianceModelRepository.findByApplianceCategoryAndBrand(category, brand);
    }

    // Retrieve appliance models by their category
    public List<ApplianceModel> getApplianceModelsByApplianceCategory(ApplianceCategory category) {
        return applianceModelRepository.findByApplianceCategory(category);
    }

    // Retrieve appliance models by their brand
    public List<ApplianceModel> getApplianceModelsByBrand(Brand brand) {
        return applianceModelRepository.findByBrand(brand);
    }

    // Save an appliance model
    @Transactional
    public ApplianceModel saveApplianceModel(ApplianceModel applianceModel) {
        return applianceModelRepository.save(applianceModel);
    }

    // Update an appliance model
    @Transactional
    public ApplianceModel updateApplianceModel(ApplianceModel applianceModel) {
        return applianceModelRepository.saveAndFlush(applianceModel);
    }
}
