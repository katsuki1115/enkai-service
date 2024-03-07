package com.example.enkai.common;

import jakarta.validation.groups.Default;
import lombok.NoArgsConstructor;
public class ValidationGroups {
    public interface Create extends Default {};

    public interface Replace extends Default {};

    public interface Update extends Default {};

    public interface Delete extends Default {};
}