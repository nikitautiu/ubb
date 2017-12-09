from django.db.models import Max
from last_modified.decorators import last_modified
from rest_framework.generics import ListCreateAPIView
from rest_framework.permissions import IsAuthenticated
from rest_framework.viewsets import ModelViewSet

from server.models import ShoppingListItem, ShoppingList
from server.serializers import ListSerializer, ListItemSerializer


class ShoppingListView(ListCreateAPIView):
    """
    A simple view for seeing all your shopping lists
    """
    serializer_class = ListSerializer
    permission_classes = [IsAuthenticated]

    def get_queryset(self):
        """Returns just the lists we own"""
        return self.request.user.lists.all()

    def perform_create(self, serializer):
        serializer.save(user=self.request.user)  # save with the current user


class ListItemView(ModelViewSet):
    serializer_class = ListItemSerializer
    permission_classes = [IsAuthenticated]
    lookup_url_kwarg = 'index'
    lookup_field = 'index'

    def get_queryset(self):
        return ShoppingListItem.objects.filter(list__id=self.kwargs['list_pk'])

    def perform_create(self, serializer):
        serializer.save(list=ShoppingList.objects.get(pk=self.kwargs['list_pk']))

    @last_modified('get_list_last_modified')
    def list(self, request, *args, **kwargs):
        return super().list(request, *args, **kwargs)

    @last_modified('get_retrieve_last_modified')
    def retrieve(self, request, *args, **kwargs):
        return super().retrieve(request, *args, **kwargs)

    @staticmethod
    def get_list_last_modified(view_instance, view_method, request, args, kwargs):
        """Get the date the object was last modified - lists
        Basically return the latest last-modified date of the list"""
        return view_instance.get_queryset().aggregate(last_date=Max('last_modified'))['last_date']

    @staticmethod
    def get_retrieve_last_modified(view_instance, view_method, request, args, kwargs):
        """Get the date the object was last modified - single object"""
        return view_instance.get_object().lastmodified
