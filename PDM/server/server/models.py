from django.conf import settings
from django.db import models


class CreatedModifiedModel(models.Model):
    created = models.DateTimeField(auto_now_add=True, editable=False, null=False, blank=False)
    last_modified = models.DateTimeField(auto_now=True, editable=False, null=False, blank=False)

    class Meta:
        abstract = True


class ShoppingList(CreatedModifiedModel):
    """The model for the shopping list"""
    text = models.CharField(max_length=128, blank=False,
                            help_text='the description of the list')  # the description of the item
    archived = models.BooleanField(help_text='whether the list is archived or not', default=False)
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE,
                             null=False, help_text='the user that owns the list', related_name='lists')  # link to user


class ShoppingListItem(CreatedModifiedModel):
    """The model for an item on the shopping list"""
    text = models.CharField(max_length=128, blank=False,
                            help_text='the description of the item')  # the description of the item
    checked = models.BooleanField(help_text='whether the items has been crossed out or not', default=False)
    list = models.ForeignKey(ShoppingList, on_delete=models.CASCADE, related_name='items')
    index = models.IntegerField(help_text='the place in the list', null=False)

    class Meta:
        ordering = ['index']  # guarantees orderin by insertion order
