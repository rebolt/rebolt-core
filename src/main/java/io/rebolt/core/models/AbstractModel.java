package io.rebolt.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class AbstractModel<T extends AbstractModel> implements Serializable {
  private static final long serialVersionUID = 4367513134262455323L;

  @JsonIgnore
  public abstract boolean isEmpty();

  @JsonIgnore
  public abstract long deepHash();

  @JsonIgnore
  public abstract boolean equals(T model);
}
