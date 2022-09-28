package co.edu.colomboamericano.caelstudent.service.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActiveStatus
{
	ACTIVE_STATUS("1");

	@JsonValue
    private final String activeStatus;

	ActiveStatus(final String activeStatus) {
        this.activeStatus = activeStatus;
    }

    @Override
    public String toString() {
        return activeStatus;
    }
}
