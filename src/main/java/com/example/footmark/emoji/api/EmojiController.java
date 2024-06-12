package com.example.footmark.emoji.api;

import com.example.footmark.emoji.api.dto.req.EmojiReqDto;
import com.example.footmark.emoji.api.dto.req.EmojiUpdateReqDto;
import com.example.footmark.emoji.api.dto.res.EmojiResDto;
import com.example.footmark.emoji.application.EmojiService;
import com.example.footmark.global.jwt.domain.CustomUserDetail;
import com.example.footmark.global.template.RspTemplate;
import com.example.footmark.review.api.dto.req.ReviewReqDto;
import com.example.footmark.review.api.dto.req.ReviewUpdateReqDto;
import com.example.footmark.review.api.dto.res.ReviewResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "emoji", description = "Emoji Controller")
@RestController
@RequestMapping("/emoji")
public class EmojiController {
    private final EmojiService emojiService;

    public EmojiController(EmojiService emojiService){
        this.emojiService = emojiService;
    }

    @Operation(summary = "이모지 등록", description = "이모지 등록합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping
    public RspTemplate<EmojiResDto> createEmoji(@Valid @RequestBody EmojiReqDto emojiReqDto, @AuthenticationPrincipal CustomUserDetail member) {

        EmojiResDto emojiResDto = emojiService.createEmoji(emojiReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "이모지 등록 성공", emojiResDto);
    }

    @Operation(summary = "이모지 수정", description = "이모지 수정합니다",parameters = {
            @Parameter(name = "createAt", description = "value = 조회 날짜, example = 2024-05-01")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PutMapping("/{createAt}")
    public RspTemplate<EmojiResDto> updateEmoji(@PathVariable String createAt, @Valid @RequestBody EmojiUpdateReqDto emojiReqDto,
                                                  @AuthenticationPrincipal CustomUserDetail member) {

        EmojiResDto emojiResDto = emojiService.updateEmoji(createAt, emojiReqDto, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "이모지 수정 성공", emojiResDto);
    }

    @Operation(summary = "이모지 삭제", description = "이모지 삭제합니다",parameters = {
            @Parameter(name = "createAt", description = "value = 조회 날짜, example = 2024-05-01")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/{createAt}")
    public RspTemplate<Void> deleteEmoji(@PathVariable String createAt,
                                          @AuthenticationPrincipal CustomUserDetail member){

        emojiService.deleteEmoji(createAt, member.getMember());

        return new RspTemplate<>(HttpStatus.OK, "이모지 삭제 성공");
    }

}
