package io.rebolt.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface IModel<T extends IModel> extends Serializable {
  @JsonIgnore
  boolean isEmpty();

  @JsonIgnore
  long deepHash();

  @JsonIgnore
  boolean equals(T model);
}
