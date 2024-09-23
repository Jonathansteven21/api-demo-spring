package online.demo.api.services;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.entities.specs.ApplianceModelSpecification;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ApplianceModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplianceModelService {

    private final ApplianceModelRepository applianceModelRepository;

    // Retrieve all appliance models
    public List<ApplianceModel> getAllApplianceModels() {
        return applianceModelRepository.findAll();
    }

    // Retrieve an appliance model by its ID
    public ApplianceModel getApplianceModelById(@NotNull(message = "ID cannot be null") Integer id) throws NotFoundException {
        return applianceModelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appliance model not found with ID: " + id));
    }

    // Retrieve an appliance model by its model
    public List<ApplianceModel> getApplianceModelByModel(@NotEmpty(message = "Model cannot be null or empty") String model) throws NotFoundException {
        List<ApplianceModel> applianceModels = applianceModelRepository.findByModel(model);
        if (applianceModels.isEmpty()) {
            throw new NotFoundException("Appliance model not found with model: " + model);
        }
        return applianceModels;
    }

    // Retrieve appliance models based on specified criteria.
    public List<ApplianceModel> getApplianceModelsByCriteria(ApplianceModel applianceModel) {
        Specification<ApplianceModel> spec = Specification.where(null);

        if (applianceModel.getModel() != null) {
            spec = spec.and(ApplianceModelSpecification.withModel(applianceModel.getModel()));
        }

        if (applianceModel.getApplianceCategory() != null) {
            spec = spec.and(ApplianceModelSpecification.withApplianceCategory(applianceModel.getApplianceCategory()));
        }

        if (applianceModel.getManufactureYear() != null) {
            spec = spec.and(ApplianceModelSpecification.withYearGreaterThanOrEqual(applianceModel.getManufactureYear()));
        }

        if (applianceModel.getBrand() != null) {
            spec = spec.and(ApplianceModelSpecification.withBrand(applianceModel.getBrand()));
        }

        List<ApplianceModel> applianceModelList = applianceModelRepository.findAll(spec);
        if (applianceModelList.isEmpty()){
            throw new NotFoundException("Filter appliance model list not found");
        }
        return applianceModelList;
    }

    // Save an appliance model
    @Transactional
    public ApplianceModel saveApplianceModel(ApplianceModel applianceModel) {
        return applianceModelRepository.save(applianceModel);
    }

    // Update an appliance model
    @Transactional
    public ApplianceModel updateApplianceModel(ApplianceModel applianceModel) {
        if (applianceModel.getId() == null) {
            throw new IllegalArgumentException("ApplianceModel ID cannot be null for update operation");
        }

        if (!applianceModelRepository.existsById(applianceModel.getId())) {
            throw new NotFoundException("ApplianceModel not found with ID: " + applianceModel.getId());
        }
        return applianceModelRepository.saveAndFlush(applianceModel);
    }
}
