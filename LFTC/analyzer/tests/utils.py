def remove_space_tokens(token_list, space_token=5):
    return list(filter(lambda x: x != space_token, (fip[0] for fip in token_list)))