from django.conf.urls import url

from server.views import ShoppingListView, ListItemView

urlpatterns = [
    url(r'lists/$', ShoppingListView.as_view()),
    url(r'lists/(?P<list_pk>.+)/items/$', ListItemView.as_view({'get': 'list', 'post': 'create'})),
    url(r'lists/(?P<list_pk>.+)/items/(?P<index>.+)/$', ListItemView.as_view({'get': 'retrieve', 'put': 'update',
                                                                              'delete': 'destroy',
                                                                              'patch': 'partial_update'})),
]
