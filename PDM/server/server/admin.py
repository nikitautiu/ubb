from django.contrib import admin
from .models import ShoppingList, ShoppingListItem

admin.site.register(ShoppingList)
admin.site.register(ShoppingListItem)
