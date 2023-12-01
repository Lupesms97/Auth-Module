package com.developement.crm.dtos;

import com.developement.crm.enums.StatusToken;

public record TokenValidation(StatusToken status, String message) {
}
