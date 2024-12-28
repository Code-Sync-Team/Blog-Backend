package code_sync_team.blog.user.application.auth.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KaKaoUserInfoResponseDto {

    @JsonProperty("kakao_account")
    public KaKaoAccount kakaoAccount;

    @NoArgsConstructor
    @JsonIgnoreProperties
    @Getter
    public class KaKaoAccount {
        @JsonProperty("email")
        public String email;
    }
}
