package io.company.brewcraft.model;

import java.net.URI;
import java.net.URL;

public interface DecoratedImageUrlAccessor {
    URI getImageSrc();
    
    void setImageUrl(URL imageUrl);
}
