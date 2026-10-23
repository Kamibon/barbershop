package com.barbershop.barber.mappers;

import com.barbershop.barber.dtos.ServiceResponse;
import com.barbershop.barber.models.Service;

public class ServiceMapper {
    public static ServiceResponse toResponse(Service service) {
        return new ServiceResponse(service.getId(), service.getName(), service.getDuration(), service.getPrice(),
                service.getImagePath());
    }
}
