package com.leantech.challenge.pojo.api;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PositionCustomSearchTO {

    private List<PositionSearchTO> positions;

}
