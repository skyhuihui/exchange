package com.zag.support.jpa.po;

import com.zag.core.model.Identifiable;

import java.util.Objects;

/**
 * 基础Po
 *
 * @author stone
 * @date 2017年04月12日
 */
public abstract class BasePo<T> implements Identifiable<T> {

	private static final long serialVersionUID = 4920583045812713490L;

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof BasePo)) {
			return false;
		}

		BasePo po = (BasePo) o;
		return getId() != null && po.getId() != null && Objects.equals(po.getId(), this.getId());
	}

	@Override
	public int hashCode() {

		return getId() != null ? getId().hashCode() : 0;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "#" + this.getId();
	}
}
