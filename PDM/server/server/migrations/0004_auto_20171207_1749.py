# -*- coding: utf-8 -*-
# Generated by Django 1.11.1 on 2017-12-07 17:49
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('server', '0003_auto_20171207_1500'),
    ]

    operations = [
        migrations.AlterModelOptions(
            name='shoppinglistitem',
            options={'ordering': ['index']},
        ),
    ]
