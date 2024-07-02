package online.lcelectronics.api.converters;

import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.dto.OrderDTO;
import online.lcelectronics.api.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderConverter {

    /**
     * Converts an Order entity to its corresponding DTO.
     * @param order The Order entity to convert.
     * @return The converted OrderDTO.
     */
    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setIssue(order.getIssue());
        dto.setProductReceivedNotes(order.getProductReceivedNotes());
        dto.setHistoricAppliance(order.getHistoricAppliance());
        dto.setImages(order.getImages());
        dto.setCreatedDate(order.getCreatedDate());
        dto.setStatus(order.getStatus());
        dto.setWarranty(order.getWarranty());
        return dto;
    }
}
