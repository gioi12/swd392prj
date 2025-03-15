package org.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfig {
    private static Cloudinary cloudinary;

    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "ddnycuhdc",
                "api_key", "874821341311516",
                "api_secret", "P3HH3RCJCrX6nXW7P5hr8w4NNyk"
        ));
    }

    public static Cloudinary getInstance() {
        return cloudinary;
    }
}
