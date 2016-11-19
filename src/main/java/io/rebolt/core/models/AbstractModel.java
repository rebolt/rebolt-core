package io.rebolt.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable {
  @JsonIgnore
  public abstract boolean isEmpty();

  @JsonIgnore
  public abstract int hashCode();
}
