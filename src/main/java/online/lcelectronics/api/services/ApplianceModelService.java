package online.lcelectronics.api.services;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.specs.ApplianceModelSpecification;
import online.lcelectronics.api.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.repositories.ApplianceModelRepository;
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
    public ApplianceModel getApplianceModelByModel(@NotEmpty(message = "Model cannot be null or empty") String model) throws NotFoundException {
        return applianceModelRepository.findByModel(model)
                .orElseThrow(() -> new NotFoundException("Appliance model not found with model: " + model));
    }

    // Retrieve appliance models based on specified criteria.
    public List<ApplianceModel> getApplianceModelsByCriteria(ApplianceModel applianceModel) {
        Specification<ApplianceModel> spec = Specification.where(ApplianceModelSpecification.modelContainsIgnoreCase(applianceModel.getModel()))
                .and(ApplianceModelSpecification.hasApplianceCategory(applianceModel.getApplianceCategory()))
                .and(ApplianceModelSpecification.yearGreaterThanOrEqual(applianceModel.getManufactureYear()))
                .and(ApplianceModelSpecification.hasBrand(applianceModel.getBrand()));

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
