from django.db.models import Max
from rest_framework import serializers

from server.models import ShoppingListItem, ShoppingList


class ListItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = ShoppingListItem
        fields = ('index', 'text', 'checked')
        read_only_fields = ('index',)

    def create(self, validated_data):
        """Create a new list item, with the index the next one in the list"""
        largest_index = validated_data['list'].items.aggregate(max_ind=Max('index'))
        validated_data['index'] = (largest_index['max_ind'] or 0) + 1
        return super().create(validated_data)


class ListSerializer(serializers.ModelSerializer):
    num_items = serializers.SerializerMethodField()
    checked_items = serializers.SerializerMethodField()

    def get_num_items(self, obj):
        return obj.items.count()  # return how many items are in total

    def get_checked_items(self, obj):
        return obj.items.filter(checked=True).count()

    class Meta:
        model = ShoppingList
        fields = ('id', 'text', 'num_items', 'checked_items')
        read_only_fields = ('id',)
