package com.abn.ems.auth;

import jakarta.validation.constraints.NotEmpty;

/**
 * Represents an authentication request containing user surName.
 *
 * <p>This record is used to encapsulate the user's credentials when making
 * an authentication request. It contains the username field,
 * both of which are mandatory for the authentication process.</p>
 *
 * @param username the username provided by the user during authentication.
 *                 Must not be null or empty.
 */
public record AuthRequest(@NotEmpty String username) {

}
