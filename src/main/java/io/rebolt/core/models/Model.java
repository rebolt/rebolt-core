package io.rebolt.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Model<T extends Model> implements IModel<T> {
  @JsonIgnore
  @Override
  public boolean equals(T model) {
    return deepHash() == model.deepHash();
  }
}
