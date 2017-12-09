from django.conf.urls import url
from rest_framework_jwt.views import verify_jwt_token, refresh_jwt_token, obtain_jwt_token

urlpatterns = [
    url(r'^tokens/auth/$', obtain_jwt_token),
    url(r'^tokens/refresh/$', refresh_jwt_token),
    url(r'^tokens/verify/$', verify_jwt_token),
]
