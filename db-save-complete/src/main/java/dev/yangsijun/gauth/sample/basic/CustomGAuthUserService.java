package dev.yangsijun.gauth.sample.basic;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.userinfo.DefaultGAuthUserService;
import dev.yangsijun.gauth.userinfo.GAuthAuthorizationRequest;
import dev.yangsijun.gauth.userinfo.GAuthUserService;

public class CustomGAuthUserService implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {

    private final GAuthUserService delegatingService = new DefaultGAuthUserService();

    private final GAuthUserRepository gauthUserRepository;

    public CustomGAuthUserService(GAuthUserRepository gauthUserRepository) {
        this.gauthUserRepository = gauthUserRepository;
    }

    @Override
    public GAuthUser loadUser(GAuthAuthorizationRequest userRequest) throws GAuthAuthenticationException {
        GAuthUser gauthUser = delegatingService.loadUser(userRequest);
        String email = gauthUser.getAttribute("email");

        gauthUserRepository.findByEmail(email)
                .orElseGet(() -> {
                    GAuthUserEntity entity = createEntity(gauthUser);
                    return gauthUserRepository.save(entity);
                });

        return gauthUser;
    }

    private GAuthUserEntity createEntity(GAuthUser gauthUser) {
        String profileUrl = gauthUser.getAttribute("profileUrl");
        String email = gauthUser.getAttribute("email");
        String name = gauthUser.getAttribute("name");
        String gender = gauthUser.getAttribute("gender");
        Integer grade = gauthUser.getAttribute("grade");
        Integer classNum = gauthUser.getAttribute("classNum");
        Integer num = gauthUser.getAttribute("num");
        String role = gauthUser.getAttribute("role");

        return new GAuthUserEntity(
                null,
                profileUrl,
                email,
                name,
                gender,
                grade,
                classNum,
                num,
                role
        );
    }
}
