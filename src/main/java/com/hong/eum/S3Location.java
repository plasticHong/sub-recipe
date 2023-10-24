package com.hong.eum;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum S3Location {

    RECIPE("recipe/"),
    PROFILE("profile/");

    public final String path;
}
