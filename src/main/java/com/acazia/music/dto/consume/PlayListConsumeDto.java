package com.acazia.music.dto.consume;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlayListConsumeDto {
    private Long playListId;
    private Long songId;
}
