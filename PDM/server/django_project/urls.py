"""zinc URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.9/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf import settings
from django.conf.urls import include, url
from django.contrib import admin

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^', include('server.urls')),
    url(r'^', include('authentication.urls')),
]


# social auth routes
if 'social_django' in settings.INSTALLED_APPS:
    urlpatterns.append(url('_auth/', include('social_django.urls', namespace='social')))


# swagger routes
try:
    from rest_framework_swagger.views import get_swagger_view
except ImportError:
    pass
else:
    schema_view = get_swagger_view(title='API')
    urlpatterns.append(url(r'^swagger/$', schema_view))


# django debug toolbar
if settings.DEBUG:
    try:
        import debug_toolbar
    except ImportError:
        pass
    else:
        urlpatterns.insert(0, url(r'^__debug__/', include(debug_toolbar.urls)))
