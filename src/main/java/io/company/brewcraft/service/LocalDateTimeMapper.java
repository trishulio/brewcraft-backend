package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class LocalDateTimeMapper {
    private ZoneId zoneId;
    private ZoneOffset offset;

    public LocalDateTimeMapper(ZoneId zoneId) {
        this.zoneId = zoneId;
        this.offset = ZoneOffset.of(this.zoneId.getId());
    }

    public LocalDateTime fromUtilDate(Date date) {
        LocalDateTime ldt = null;

        if (date != null) {
            ldt = LocalDateTime.ofInstant(date.toInstant(), this.zoneId);
        }

        return ldt;
    }

    public Date toUtilDate(LocalDateTime dt) {
        Date date = null;

        if (dt != null) {
            date = Date.from(dt.toInstant(this.offset));
        }

        return date;
    }
}
